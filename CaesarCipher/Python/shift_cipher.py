alphabet = ''

def create_substitution_table(shift):
    substitution_table = str.maketrans(alphabet, alphabet[shift:] + alphabet[:shift])
    return substitution_table

def encrypt(plaintext, substitution_table):
    return plaintext.translate(substitution_table)

def decrypt(ciphertext, substitution_table):
    return ciphertext.translate(substitution_table)

alphabet = input("Enter alphabet:\n")
plaintext = input("Enter plaintext:\n")
shift = int(input("Enter shift:\n"))

substitution_table = create_substitution_table(shift)
ciphertext = encrypt(plaintext, substitution_table)

print(f'\nPlaintext:\n{plaintext}')
print(f'Ciphertext:\n{ciphertext}')

substitution_table = create_substitution_table(-shift)
plaintext = decrypt(ciphertext, substitution_table)

print(f'Decrypted text:\n{plaintext}')

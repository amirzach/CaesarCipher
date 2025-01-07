alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

def create_substitution_table(shift):
    substitution_table = str.maketrans(alphabet, alphabet[shift:] + alphabet[:shift])
    return substitution_table

def encrypt(plaintext, substitution_table):
    return plaintext.translate(substitution_table)

def decrypt(ciphertext, substitution_table):
    return ciphertext.translate(substitution_table)

plaintext = input("\nEnter plaintext: ")
shift = int(input("\nEnter shift: "))

substitution_table = create_substitution_table(shift)
ciphertext = encrypt(plaintext, substitution_table)

print(f'\nPlaintext: {plaintext}')
print(f'\nCiphertext: {ciphertext}')

substitution_table = create_substitution_table(-shift)
plaintext = decrypt(ciphertext, substitution_table)

print(f'\nDecrypted text: {plaintext}\n')